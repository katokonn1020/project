import scala.sys.process._
import java.io.File

import scala.collection.immutable
import scala.util.matching.Regex

object MoveDirectry {
  def main(args: Array[String]): Unit = {
    val currentPath = new File(".").getAbsoluteFile.getParent
    val dirList = Process(Seq("sh", "-c", "cd ../before/src && ls")).lines.toList
    dirList.foreach(getDomainName)
  }

  def getDomainName(name: String) {
    val gitText: String = Process(Seq("sh", "-c", s"git -C ../before/src/$name remote -v")).lines.head.dropRight(8)
    //"project5.git (fetch)" or "project4 (fetch)"と2パターンあったのでifで処理
    val collectedGitText: String = gitText.dropRight(if (gitText.endsWith(".git")) 4 else 0)
    //ドメインを自動的に取得するように。記述は不要。ただし現状.comか.infoで終わる物のみ。
    val domainRegex = """[@|/].+com|info""".r
    val currentDomainName = domainRegex.findFirstIn(gitText).get.diff("/").diff("/").diff("@")
    mkdir(currentDomainName, collectedGitText)
  }

  def mkdir(domainName: String, collectedGitText: String): Unit = {
    val dirName: String = collectedGitText.split(domainName + ".").last.split("/").toSeq.mkString("/")
    Process(Seq("sh", "-c", s"mkdir -p ../before/src/$domainName/$dirName")).run
    println(s"Sucess!\n$domainName/$dirName")
  }
}
