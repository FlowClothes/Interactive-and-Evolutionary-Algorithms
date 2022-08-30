package tools.experimentsDataDealing.steps;

import java.io.File;

/**
 *
 * @author hgs
 */
public class D0RenameFile {

    public static void main(String[] args) {
        String director = "E:\\tem\\myEAs\\data\\F0\\binaryCode", searchedName = "F0max_xsin10PIx_2.0with dimension D_1_", candidateName = "D";
        File file = new File(director);
        if (file.isDirectory()) {
            File[] filelist = file.listFiles();
            for (int i = 0; i < filelist.length; i++) {
                if (filelist[i].isFile()) {
                    String filename = filelist[i].getName();
                    if (filename.contains(searchedName)) {
                        String newname = director+"\\"+filename.replace(searchedName, candidateName);
                        filelist[i].renameTo(new File(newname));
//                        try {
//                            Files.move(filelist[i].toPath(), new File(newname).toPath(), StandardCopyOption.ATOMIC_MOVE);
//                            System.out.println("");
//                        } catch (IOException ex) {
//                            Logger.getLogger(RenameFile.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                    }
                }
            }
        }
    }
}
