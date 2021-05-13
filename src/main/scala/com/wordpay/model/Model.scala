package com.wordpay.model


object Model {

  sealed trait WorldPaySettlementRecord

  case class WorldPayT00(name: String) extends WorldPaySettlementRecord

  case class WorldPayT05(cardType: String) extends WorldPaySettlementRecord

  case class WorldPayT10(bu: String) extends WorldPaySettlementRecord

  case class WorldpayRecord(t15: WorldPayT15, t16: WorldPayT16, t19: Option[WorldPayT19]) extends WorldPaySettlementRecord

  case class WorldPayT19(name: String)

  case class WorldPayT15(name: String)

  case class WorldPayT16(name: String, token: String, cardNo: String = "")

}
