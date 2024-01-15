package org.example;

import org.example.exceptions.FileException;

import java.io.*;
import java.util.ResourceBundle;

public class FileSudokuBoardDao implements IDao<SudokuBoard> {
    private String fileName;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName + ".txt";
    }

    @Override
    public SudokuBoard read() throws FileException {
        SudokuBoard obj = null;

        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            obj = (SudokuBoard) objectInputStream.readObject();
        } catch (ClassNotFoundException exception) {
            ResourceBundle bundle = ResourceBundle.getBundle("ExceptionMessages");
            String message = bundle.getString("org.example.CloneException");
            throw new RuntimeException(message);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        return obj;
    }

    @Override
    public void write(SudokuBoard obj) throws FileException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(obj);
        }   catch (IOException exception) {
            ResourceBundle bundle = ResourceBundle.getBundle("ExceptionMessages");
            String message = bundle.getString("org.example.CloneException");
            throw new RuntimeException(message);
        }
    }

    @Override
    public void close() throws IOException {
        if (objectInputStream != null) {
            objectInputStream.close();
        }
        if (fileInputStream != null) {
            fileInputStream.close();
        }

        if (objectOutputStream != null) {
            objectOutputStream.close();
        }
        if (fileOutputStream != null) {
            fileOutputStream.close();
        }
    }
}
