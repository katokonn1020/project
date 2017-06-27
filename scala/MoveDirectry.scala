import scala.sys.process._
import java.io.File

import scala.collection.immutable
import scala.util.matching.Regex

object MoveDirectry {
  def main(args: Array[String]): Unit = {
    val dirList = Process(Seq("sh", "-c", "cd ../before/src && ls")).lines.toList
    dirList.foreach { projectName =>
      val gitText: String = Process(Seq("sh", "-c", s"git -C ../before/src/$projectName remote -v")).lines.head.dropRight(8)
      val currentDomainName = getDomainName(gitText)
      val dirNameArray: Array[String] = gitText.dropRight(if (gitText.endsWith(".git")) 4 else 0).split(currentDomainName + ".").last.split("/")
      mkdir(dirNameArray, currentDomainName, gitText)
      mv(projectName, currentDomainName, dirNameArray)
    }
  }

  //ドメインを自動的に取得するように。記述は不要。ただし現状.comか.infoで終わる物のみ。
  def getDomainName(gitText: String): String = {
    val domainRegex = """[@|/].+com|info""".r
    domainRegex.findFirstIn(gitText).get.diff("/").diff("/").diff("@")
  }

  def mkdir(dirNameArray: Array[String], domainName: String, gitText: String): Unit = {
    val dirName: String = dirNameArray.init.toSeq.mkString("/")
    Process(Seq("sh", "-c", s"mkdir -p ../before/src/$domainName/$dirName")).run
    println(s"Sucess!\n$domainName/$dirName")
  }

  def mv(projectName: String, domainName: String, dirNameArray: Array[String]) = {
    //現在のリポジトリ名が「project05」でも、gitのリポジトリ名が「project5」など差分がある可能性があるので用意しておく。
    val gitProjectDirName: String = dirNameArray.last
    //mvをする際に、src/project05 -> src/github/shokohara/project5 というふうにdir名が異なっていた場合mvができないので先にリネーム
    Process(Seq("sh", "-c", s"mv ../before/src/$projectName ../before/src/$gitProjectDirName")).run
    //「p/shokohara-123456/r/project5」のようにfullPathString
    val fullDirName: String = dirNameArray.toSeq.mkString("/")
    Process(Seq("sh", "-c", s"mv ../before/src/$gitProjectDirName/ ../before/src/$domainName/$fullDirName")).run
  }
}
