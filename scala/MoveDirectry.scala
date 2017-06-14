import scala.sys.process._
import java.io.File

object MoveDirectry {
  def main(args: Array[String]): Unit = {
    //val process = Process("mkdir sample").run
    //val currentPath = new File(".").getAbsoluteFile.getParent
    //val process = Process("git remote -v").lines.toList
    //Process(Seq("cd","../","mkdir","sample"), new File(currentPath)).!
    val dirList = Process(Seq("ls"), new File("/Users/seiji/git/project2/project/before/src")).lines.toList
    println(dirList.toList(0))
    //val process = Process("git remote -v").lines.toList
  }
}
