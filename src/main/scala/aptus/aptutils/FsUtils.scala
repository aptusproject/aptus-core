package aptus
package aptutils

import org.apache.commons.io

// ===========================================================================
object FsUtils {
  private def file(value: FilePath) = new java.io.File(value.replace("~", aptus.fs.homeDirectoryPath()))

  // ---------------------------------------------------------------------------
  def moveFile(from: FilePath, to: FilePath): FilePath = {
        require( from.path.exists(), from)
        require(!to  .path.exists(), to)
          io.FileUtils.moveFile(file(from), file(to))
        assert(!from.path.exists(), from)
        assert( to  .path.exists(), to)
      to }

    // ---------------------------------------------------------------------------
    def moveFileToDir(from: FilePath, to: DirPath): FilePath = {
        require(to  .path.isDir(), to)

        require(from.path.exists(), from); val to2 = to / file(from).getName
        require(!to2.path.exists(), to)
          io.FileUtils.moveFile(file(from), file(to2))
        require(!from.path.exists(), from)
        require( to2.path.exists(), to)

      to2 }
}

// ===========================================================================