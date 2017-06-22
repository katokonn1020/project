import scala.sys.process._
import java.io.File

import scala.collection.immutable
import scala.util.matching.Regex

object MoveDirectry {
  def main(args: Array[String]): Unit = {
    val currentPath = new File(".").getAbsoluteFile.getParent
    val dirList = Process(Seq("sh","-c","cd ../before/src && ls")).lines.toList
    dirList.foreach(mkdir)
  }

  def mkdir(name: String) {
    val gitText: String = Process(Seq("sh", "-c", s"git -C ../before/src/$name remote -v")).lines.head.dropRight(8)
    //"project5.git (fetch)" or "project4 (fetch)"と2パターンあったのでifで処理
    val collectedGitText: String = if (gitText.endsWith(".git")) gitText.dropRight(4) else gitText
    //リポジトリ名を先に記しておかないといけない仕様
    val githubWith = "(.*github.com.*)".r
    val googleWith = "(.*source.developers.google.com.*)".r
    val awsWith = "(.*git-codecommit.us-east-1.amazonaws.com.*)".r
    val signatedDomainName:String =
      collectedGitText match {
        case githubWith(matched) => "github.com"
        case googleWith(matched) => "source.developers.google.com"
        case awsWith(matched) => "git-codecommit.us-east-1.amazonaws.com"
        case _ => ""
      }
    temp(signatedDomainName,collectedGitText)
  }

  def temp(domainName: String,collectedGitText: String): Unit ={
    val dirName: String = collectedGitText.split(domainName+".").last.split("/").toSeq.mkString("/")
    Process(Seq("sh", "-c", s"mkdir -p ../before/src/$domainName/" + dirName)).run
  }
}
