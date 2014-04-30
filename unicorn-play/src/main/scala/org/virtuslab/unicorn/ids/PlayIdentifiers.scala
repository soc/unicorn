package org.virtuslab.unicorn.ids

import play.api.data.format.{Formats, Formatter}
import play.api.mvc.{QueryStringBindable, PathBindable}
import scala.slick.driver.JdbcDriver

trait PlayIdentifiers extends Identifiers {
  self: JdbcDriver =>

  abstract class PlayCompanion[Id <: BaseId]
    extends CoreCompanion[Id]
    with Applicable[Id]
    with PlayImplicits[Id]

  /** Marker trait */
  protected[unicorn] trait Applicable[Id <: BaseId] {

    /**
     * Factory method for I instance creation.
     * @param id long from which I instance is created
     * @return I instance
     */
    def apply(id: Long): Id
  }

  /**
   * Implicits required by Play.
   *
   * @tparam Id type of Id
   * @author Krzysztof Romanowski, Jerzy Müller
   */
  protected[unicorn] trait PlayImplicits[Id <: BaseId] {
    self: Applicable[Id] =>

    /**
     * Type mapper for route files.
     * @param longBinder path bindable for Long type.
     * @return path bindable for I
     */
    implicit def pathBindable(implicit longBinder: PathBindable[Long]): PathBindable[Id] =
      longBinder.transform(apply, _.id)

    /** Implicit for mapping id to routes params for play */
    implicit val toPathBindable: QueryStringBindable[Id] =
      QueryStringBindable.bindableLong.transform(apply, _.id)

    /** Form formatter for I */
    implicit lazy val idMappingFormatter: Formatter[Id] = new Formatter[Id] {

      override val format = Some(("format.numeric", Nil))

      override def bind(key: String, data: Map[String, String]) =
        Formats.longFormat.bind(key, data).right.map(apply).left.map {
          case errors if data.get(key).forall(_.isEmpty) => errors.map(_.copy(message = "id.empty"))
          case errors => errors.map(_.copy(message = "id.invalid"))
        }

      override def unbind(key: String, value: Id): Map[String, String] =
        Map(key -> value.id.toString)
    }
  }

}
