import scala.sys.process._
import java.io.File

object MoveDirectry {
  def main(args: Array[String]): Unit = {
    val currentPath = new File(".").getAbsoluteFile.getParent
    val dirList = Process(Seq("sh","-c","cd ../before/src && ls")).lines.toList
    dirList.foreach(mkdir)
  }
 
  def mkdir(name: String){
    //死んでる変数ですが、どうやっても""の中に変数を入れる方法が見つからなかったので、一旦stringに格納
    val gitPlainText = "git -C ../before/src/"+name+" remote -v"
    val gitText = Process(Seq("sh","-c",gitPlainText)).lines.toList(0)

    //リポジトリ名を先に記しておかないといけない仕様
    val githubWith = "(.*github.com.*)".r
    val googleWith = "(.*source.developers.google.com.*)".r
    val awsWith = "(.*git-codecommit.us-east-1.amazonaws.com.*)".r

      gitText match {
        case githubWith(matched) => 
         gitText.split("github.com.").last.split("/").init.foreach()
       
        case _ =>
      }
      gitText match {
        case googleWith(matched) => 
          //println(gitText.split("source.developers.google.com.")(1))
        case _ =>
      }
      gitText match {
        case awsWith(matched) =>
          //println(gitText.split("git-codecommit.us-east-1.amazonaws.com.")(1))
        case _ =>
      }

  }
}
