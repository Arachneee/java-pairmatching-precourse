package pairmatching.domain;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import pairmatching.domain.constant.Course;
import pairmatching.exception.ErrorMessage;
import pairmatching.exception.PairMatchingException;

public final class Initializer {

    private Initializer() {
    }

    public static void initCrew(final Course course, final String fileName) {
        final FileInputStream file = readFile(fileName);
        saveCrews(course, file);
    }

    private static FileInputStream readFile(final String fileName) {
        final ClassLoader classLoader = Initializer.class.getClassLoader();
        FileInputStream file = null;
        try {
            file = new FileInputStream(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        } catch (FileNotFoundException e) {
            throw new PairMatchingException(ErrorMessage.FILE_NOT_FOUND);
        }
        return file;
    }

    private static void saveCrews(Course course, FileInputStream file) {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file));

        while (true) {
            final String crewName;
            try {
                crewName = bufferedReader.readLine();
            } catch (IOException e) {
                throw new PairMatchingException(ErrorMessage.IO);
            }
            if (crewName == null) {
                break;
            }
            CrewRepository.addCrew(new Crew(course, crewName));
        }
    }

}
