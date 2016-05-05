package models.daos

import javax.inject.Inject

import com.mohiva.play.silhouette.api.LoginInfo
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.lifted.ProvenShape.proveShapeOf

import scala.concurrent.Await
//TODO needs injection
class CreateSchema @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends DBTableDefinitions {
  val driver = slick.driver.MySQLDriver
  import driver.api._

  lazy val allTables = Array(
    TableQuery[Users].schema,
    TableQuery[LoginInfos].schema,
    TableQuery[UserLoginInfos].schema,
    TableQuery[PasswordInfos].schema
  ).reduceLeft(_ ++ _)

  def create() = {
    //    allTables.create
    //    TableQuery[Users].schema.create

    val coffees = TableQuery[Users]
    coffees.schema.create
  }
}
