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
println(gitText)

    val githubWith = "(.*github.com.*)".r
    val googleWith = "(.*source.developers.google.com.*)".r
    val awsWith = "(.*git-codecommit.us-east-1.amazonaws.com.*)".r
    val dirText =
      gitText match {
        case githubWith(matched) => gitText.split("github.com") 
        case _ => println("Unmatched!")
      }
      gitText match {
        case googleWith(matched) => println("this is google") // "abcdefg"
        case _ => println("Unmatched!")
      }
      gitText match {
        case awsWith(matched) => println("this is aws") // "abcdefg"
        case _ => println("Unmatched!")
      }









    //Process(Seq("sh","-c","cd ../before/src && mkdir "))
  }
}
