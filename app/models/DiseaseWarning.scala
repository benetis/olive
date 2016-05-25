package models

case class DiseaseWarning(id: Option[Long] = None,
                                 modelId: Long,
                                 userId: Long
                                ){}