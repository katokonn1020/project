import scala.sys.process._
import java.io.File

import scala.collection.immutable

object MoveDirectry {
  def main(args: Array[String]): Unit = {
    val currentPath = new File(".").getAbsoluteFile.getParent
    val dirList     = Process(Seq("sh","-c","cd ../before/src && ls")).lines.toList
    dirList.foreach(mkdir)
  }

  def mkdir(name: String) {
    //死んでる変数ですが、どうやっても""の中に変数を入れる方法が見つからなかったので、一旦stringに格納
    //val gitText = Process(Seq("sh","-c","git -C ../before/src/"+name+" remote -v")).lines.toList(0)
    val gitText: String = Process(Seq("sh", "-c", "git -C ../before/src/" + name + " remote -v")).lines.head.dropRight(8)

    //"project5.git (fetch)" or "project4 (fetch)"と2パターンあったのでifで処理
    val collectedGitText: String =
      if (gitText.endsWith(".git")) {
        gitText.dropRight(4)
      } else {
        gitText
      }

    //リポジトリ名を先に記しておかないといけない仕様
    val githubWith = "(.*github.com.*)".r
    val googleWith = "(.*source.developers.google.com.*)".r
    val awsWith    = "(.*git-codecommit.us-east-1.amazonaws.com.*)".r

    collectedGitText match {
      case githubWith(matched) =>
        val dirName: String = collectedGitText.split("github.com.").last.split("/").toSeq.mkString("/")
        println(dirName)
        Process(Seq("sh", "-c", "mkdir -p ../before/src/github.com/" + dirName)).run
      case _ =>
    }
    collectedGitText match {
      case googleWith(matched) =>
        val dirName: String = collectedGitText.split("source.developers.google.com.").last.split("/").toSeq.mkString("/")
        println(dirName)
        Process(Seq("sh", "-c", "mkdir -p ../before/src/source.developers.google.com/" + dirName)).run
      case _ =>
    }
    collectedGitText match {
      case awsWith(matched) =>
        val dirName: String = collectedGitText.split("git-codecommit.us-east-1.amazonaws.com.").last.split("/").toSeq.mkString("/")
        println(dirName)
        Process(Seq("sh", "-c", "mkdir -p ../before/src/git-codecommit.us-east-1.amazonaws.com./" + dirName)).run
      case _ =>
    }
  }
}
