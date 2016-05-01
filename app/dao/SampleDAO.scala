package dao

import javax.inject.Inject

import models.Sample
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import slick.lifted.ProvenShape

class SampleDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val samples = TableQuery[SamplesTable]

  private class SamplesTable(tag: Tag) extends Table[Sample](tag, "SAMPLE") {
    override def * : ProvenShape[Sample] = ???
  }
}
