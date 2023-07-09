import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Interface commune pour les composants de l'arbre
interface ComposantSystFichiers {
    void display(int niveau);
}

// Classe représentant les fichiers
class FileComposite implements ComposantSystFichiers {
    private String name;


    public FileComposite(String name) {
        this.name = name;
    }

    public void display(int niveau) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < niveau; i++) {
            indent.append("|  ");
        }

        System.out.println(indent + "|-- " + name);
    }
}

// Classe représentant les répertoires
class DirectoryComposite implements ComposantSystFichiers {
    private String name;
    private List<ComposantSystFichiers> children = new ArrayList<>();

    public DirectoryComposite(String name) {
        this.name = name;
    }

    public void add(ComposantSystFichiers component) {
        children.add(component);
    }

    public void display(int niveau) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < niveau; i++) {
            indent.append("|  ");
        }

        System.out.println(indent + "|─── " + name);

        for (ComposantSystFichiers component : children) {
            component.display(niveau + 1);
        }
    }
}

public class Tree {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Veuillez spécifier un chemin de répertoire.");
            return;
        }

        String directoryPath = args[0];
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            Tree tree = new Tree();
            DirectoryComposite root = new DirectoryComposite(directory.getName());
            tree.buildTree(directory, root);
            root.display(0);
        } else {
            System.out.println("Le répertoire n'existe pas.");
        }
    }

    private void buildTree(File directory, DirectoryComposite parent) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    DirectoryComposite subDirectory = new DirectoryComposite(file.getName());
                    parent.add(subDirectory);
                    buildTree(file, subDirectory);
                } else {
                    FileComposite FileComposite = new FileComposite(file.getName());
                    parent.add(FileComposite);
                }
            }
        }
    }
}
