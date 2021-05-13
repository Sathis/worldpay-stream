package com.wordpay.stream

import java.io.{File, PrintWriter}
import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl.{FileIO, Framing, Sink}
import akka.util.ByteString
import com.wordpay.model.Model.{WorldPaySettlementRecord, WorldPayT00, WorldpayRecord}
import org.beanio.StreamFactory
import play.api.libs.json.Json

import scala.concurrent.Future
import scala.util.{Failure, Success}

object WorldPayStreamProcessor extends App {

  println(WorldPayT00.getClass.getName)
  implicit val system = ActorSystem("AlpakkaSlickDemo")

  implicit val materializer = Materializer(system)
  implicit val ec = materializer.executionContext

  def updateLine(line: WorldPaySettlementRecord): Future[WorldPaySettlementRecord] = line match {
      case WorldpayRecord(t15, t16, t19) =>
        Future(WorldpayRecord(t15, t16.copy(cardNo = "123"), t19))
      case _ =>
        Future.successful(line)
    }

  // create a StreamFactory
  val factory = StreamFactory.newInstance
  // load the mapping file
  factory.load("src/main/resources/beanio.mapping.xml")

  // use a StreamFactory to create a BeanReader
  val out = factory.createWriter("worldPayT00", new PrintWriter(System.out))


  val done = FileIO.fromPath(Paths.get("src/main/resources/mapping.txt"))
    .via(Framing.delimiter(ByteString("\n"),
      4096, true)
    )
    .map(line => Json.parse(line.utf8String).as[WorldPaySettlementRecord])
    .mapAsync(1){ line =>
      updateLine(line)
    }
    .runWith(Sink.foreach{ record =>
      record match {
        case t00@WorldPayT00(_)  => out.write(t00)
        case _ => println
      }
    })

  done.onComplete{
    case Success(value) => {
      out.flush
      out.close
      system.terminate
    }
    case Failure(exception) =>
      exception.printStackTrace()
      system.terminate
  }
}
