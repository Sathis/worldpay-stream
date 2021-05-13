package com.wordpay

import com.wordpay.model.Model._
import play.api.libs.json.{Json, Writes, __}
import play.api.libs.functional.syntax._

package object model {

  implicit val t15Read = Json.reads[WorldPayT15]
  implicit val t16Read = Json.reads[WorldPayT16]
  implicit val t19Read = Json.reads[WorldPayT19]

  implicit val t15Write = Json.writes[WorldPayT15]
  implicit val t16Write = Json.writes[WorldPayT16]
  implicit val t19Write = Json.writes[WorldPayT19]

  implicit val worldPaySettlementRecordReads = {
    implicit val t00 = Json.reads[WorldPayT00]
    implicit val t05 = Json.reads[WorldPayT05]
    implicit val t10 = Json.reads[WorldPayT10]
    implicit val worldpayRecord = Json.reads[WorldpayRecord]


    __.read[WorldPayT00](t00).map(x => x: WorldPaySettlementRecord) |
      __.read[WorldPayT05](t05).map(x => x: WorldPaySettlementRecord) |
      __.read[WorldPayT10](t10).map(x => x: WorldPaySettlementRecord) |
      __.read[WorldpayRecord](worldpayRecord).map(x => x: WorldPaySettlementRecord)
  }


  implicit val worldPaySettlementRecordWrites = Writes[WorldPaySettlementRecord] {
    case t00: WorldPayT00 => Json.writes[WorldPayT00].writes(t00)

    case t05: WorldPayT05 => Json.writes[WorldPayT05].writes(t05)
    case t10: WorldPayT10 => Json.writes[WorldPayT10].writes(t10)
    case worldPayRecord: WorldpayRecord => Json.writes[WorldpayRecord].writes(worldPayRecord)
  }

}
