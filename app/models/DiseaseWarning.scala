package models

import java.util.UUID

case class DiseaseWarning(id: Option[Long] = None,
                                 modelId: Long,
                                 userId: UUID
                                ){}