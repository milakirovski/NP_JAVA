package kolok1.zad3_FileSystem;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class IndentPrinter{
    public static String printIndent(int level){
        return IntStream.range(0,level)
                .mapToObj(i -> " ")
                .collect(Collectors.joining());
    }
}

class FileNameExistsException extends Exception{
    public FileNameExistsException(String fileName, String folderName){
        super(String.format("There is already a file named %s in the folder %s",
                fileName, folderName));
    }
}

interface IFile extends Comparable<IFile>{
    String getFileName();
    long getFileSize();
    String getFileInfo(int indent);
    void sortBySize();
    long findLargestFile();
}

class File implements IFile{

    protected String fileName;
    protected long fileSize;

    public File(String fileName) {
        this.fileName = fileName;
        this.fileSize = 0L;
    }

    public File(String fileName, long fileSize) {
        this.fileSize = fileSize;
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public long getFileSize() {
        return this.fileSize;
    }

    @Override
    public String getFileInfo(int indent) {
        return String.format("%sFile name: %10s File size: %10d\n",
                IndentPrinter.printIndent(indent),this.getFileName(),this.getFileSize());
    }

    @Override
    public void sortBySize() {
        return ;
    }

    @Override
    public long findLargestFile() {
        return this.fileSize;
    }

    @Override
    public int compareTo(IFile o) {
        return Long.compare(this.getFileSize(), o.getFileSize());
    }
}

class Folder extends File implements IFile{
    private List<IFile> files;

    public Folder(String fileName) {
        super(fileName);
        this.files = new ArrayList<>();
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public long getFileSize() {
        return files.stream().mapToLong(IFile::getFileSize).sum();
    }

    @Override
    public String getFileInfo(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%sFolder name: %10s Folder size: %10d\n",
                IndentPrinter.printIndent(indent),fileName, this.getFileSize()));

        files.forEach(i -> sb.append(i.getFileInfo(indent + 1)));

        return sb.toString();
    }

    @Override
    public void sortBySize() {
        // vnimavaj vo Folder si morash rekurzivno ! =)

        files.sort(Comparator.naturalOrder());
        files.forEach(IFile::sortBySize);
    }

    @Override
    public long findLargestFile() {
        // First find the largest file in the current folder
        long largestInCurrentFolder = files.stream()
                .filter(file -> file.getClass() == File.class)
                .mapToLong(IFile::getFileSize)
                .max()
                .orElse(0L);

        // Then find the largest file recursively in all subfolders
        long largestInSubFolders = files.stream()
                .filter(file -> file instanceof Folder)
                .mapToLong(IFile::findLargestFile)
                .max()
                .orElse(0L);

        // Return the largest of both
        return Math.max(largestInCurrentFolder, largestInSubFolders);
    }

    public void addFile(IFile ifile) throws FileNameExistsException {
        if(checkIfFileAlreadyExists(ifile)){
            throw new FileNameExistsException(ifile.getFileName(),this.fileName);
        }
        files.add(ifile);
    }

    private boolean checkIfFileAlreadyExists(IFile file){
        return files.stream().map(IFile::getFileName).anyMatch(i -> i.equals(file.getFileName()));
    }
}

class FileSystem{

    private Folder root;
    public FileSystem() {
        this.root = new Folder("root");
    }

    public void addFile(IFile ifile) throws FileNameExistsException {
        root.addFile(ifile);
    }

    public void sortBySize() {
        root.sortBySize();
    }

    public long findLargestFile() {
        return root.findLargestFile();
    }

    @Override
    public String toString() {
        return this.root.getFileInfo(0);
    }

}

public class FileSystemTest {

    public static Folder readFolder (Scanner sc)  {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i=0;i<totalFiles;i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String [] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return folder;
    }

    public static void main(String[] args)  {

        //file reading from input

        Scanner sc = new Scanner (System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());
    }
}
