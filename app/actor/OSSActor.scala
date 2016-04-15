package actor

import java.io.FileInputStream
import javax.inject.Inject

import akka.actor.Actor
import com.aliyun.oss.model.ObjectMetadata
import modules.OSSClientProvider
import play.Logger
import play.api.Configuration
import play.api.libs.Files.TemporaryFile

/**
  * Created by handy on 15/11/17.
  * kakao china
  */
case class OSS (file:TemporaryFile,key:String)

class OSSActor @Inject() (oss_client : OSSClientProvider,configuration: Configuration) extends Actor{

  override def receive = {
    case oss:OSS =>
      val is = new FileInputStream(oss.file.file)
      val objMetadata = new ObjectMetadata()
      objMetadata.setContentLength(oss.file.file.length())
      objMetadata.setContentType(oss.file.file.getName.replaceFirst("^[^.]*", ""))
      val result = oss_client.get.putObject(configuration.getString("oss.bucket").getOrElse(""), oss.key, is, objMetadata)
      Logger.debug("oss import success " + result.getETag)
  }

}

