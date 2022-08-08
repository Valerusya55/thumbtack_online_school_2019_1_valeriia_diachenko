package net.thumbtack.school.threads;
import java.io.Serializable;
import java.util.*;

import static java.util.Comparator.*;
public class Task9 {
    public enum TrainingErrorCode {
        TRAINEE_WRONG_FIRSTNAME("Trainee wrong first name"),
        TRAINEE_WRONG_LASTNAME("Trainee wrong last name"),
        TRAINEE_WRONG_RATING("Trainee wrong rating"),
        GROUP_WRONG_NAME("Group wrong name"),
        GROUP_WRONG_ROOM("Group wrong room"),
        TRAINEE_NOT_FOUND("Trainee not found"),
        SCHOOL_WRONG_NAME("School wrong name"),
        DUPLICATE_GROUP_NAME("Duplicate group name"),
        GROUP_NOT_FOUND("Group not found"),
        DUPLICATE_TRAINEE("Duplicate trainee"),
        EMPTY_TRAINEE_QUEUE("Empty trainee queue");

        private String errorString;

        TrainingErrorCode(String errorString) {
            this.errorString = errorString;
        }

        public String getErrorString() {
            return errorString;
        }
    }
    public class TrainingException extends Exception {
        private TrainingErrorCode trainingErrorCode;

        public TrainingException(TrainingErrorCode trainingErrorCode) {
            this.trainingErrorCode = trainingErrorCode;
        }

        public TrainingErrorCode getErrorCode() {
            return trainingErrorCode;
        }
    }

    public class Trainee implements Serializable {
        private String firstName;
        private String lastName;
        private int rating;

        public Trainee(String firstName, String lastName, int rating) throws TrainingException {
            setFirstName(firstName);
            setLastName(lastName);
            setRating(rating);
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) throws TrainingException {
            if (firstName == null || firstName.length() == 0) {
                throw new TrainingException(TrainingErrorCode.TRAINEE_WRONG_FIRSTNAME);
            }
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) throws TrainingException {
            if (lastName == null || lastName.length() == 0) {
                throw new TrainingException(TrainingErrorCode.TRAINEE_WRONG_LASTNAME);
            }
            this.lastName = lastName;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) throws TrainingException {
            if (rating < 1 || rating > 5) {
                throw new TrainingException(TrainingErrorCode.TRAINEE_WRONG_RATING);
            }
            this.rating = rating;
        }

        public String getFullName() {
            return firstName + " " + lastName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Trainee trainee = (Trainee) o;
            return rating == trainee.rating &&
                    Objects.equals(firstName, trainee.firstName) &&
                    Objects.equals(lastName, trainee.lastName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstName, lastName, rating);
        }
    }

    public class Group {
        private String name;
        private String room;
        private List<Trainee> trainees;

        public Group(String name, String room) throws TrainingException {
            setName(name);
            setRoom(room);
            this.trainees = Collections.synchronizedList(new ArrayList<>());
        }

        public String getName() {
            return name;
        }

        public synchronized void setName(String name) throws TrainingException {
            if (name == null || name.length() == 0) {
                throw new TrainingException(TrainingErrorCode.GROUP_WRONG_NAME);
            }
            this.name = name;
        }

        public String getRoom() {
            return room;
        }

        public synchronized void setRoom(String room) throws TrainingException {
            if (room == null || room.length() == 0) {
                throw new TrainingException(TrainingErrorCode.GROUP_WRONG_ROOM);
            }
            this.room = room;
        }

        public List<Trainee> getTrainees() {
            return trainees;
        }

        public void addTrainee(Trainee trainee) {
            trainees.add(trainee);
        }

        public synchronized void removeTrainee(Trainee trainee) throws TrainingException {
            if (!trainees.remove(trainee)) {
                throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
            }
        }

        public synchronized void removeTrainee(int index) throws TrainingException {
            if (index < 0 || index >= trainees.size()) {
                throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
            }
            trainees.remove(index);
        }

        public Trainee getTraineeByFirstName(String firstName) throws TrainingException {
            for (Trainee trainee : trainees) {
                if (trainee.getFirstName().equals(firstName)) {
                    return trainee;
                }
            }
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }

        public Trainee getTraineeByFullName(String fullName) throws TrainingException {
            for (Trainee trainee : trainees) {
                if (trainee.getFullName().equals(fullName)) {
                    return trainee;
                }
            }
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }

        public void sortTraineeListByFirstNameAscendant() {
            trainees.sort(comparing(Trainee::getFirstName));
        }

        public void sortTraineeListByRatingDescendant() {
            trainees.sort(comparing(Trainee::getRating).reversed());
        }

        public void reverseTraineeList() {
            Collections.reverse(trainees);
        }

        public void rotateTraineeList(int positions) {
            Collections.rotate(trainees, positions);
        }

        public List<Trainee> getTraineesWithMaxRating() throws TrainingException {
            int maxRating = 0;
            List<Trainee> result = new ArrayList<>();
            if (trainees == null || trainees.size() == 0) {
                throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
            }
            sortTraineeListByRatingDescendant();
            for (Trainee trainee : trainees) {
                if (trainees.get(0).getRating() == trainee.getRating()) {
                    result.add(trainee);
                }
            }
            return result;
        }

        public boolean hasDuplicates() {
            return new HashSet<Trainee>(trainees).size() < trainees.size();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Group group = (Group) o;
            return Objects.equals(name, group.name) &&
                    Objects.equals(room, group.room) &&
                    Objects.equals(trainees, group.trainees);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, room, trainees);
        }
    }
    public class School {
        private String name;
        private int year;
        private Set<Group> groups;

        public School(String name, int year) throws TrainingException {
            setName(name);
            this.year = year;
            this.groups = Collections.synchronizedSet(new TreeSet<Group>((p1,p2) -> p1.getName().compareTo(p2.getName())));
        }

        public String getName() {
            return name;
        }

        public synchronized void setName(String name) throws TrainingException {
            if (name == null || name.length() == 0) {
                throw new TrainingException(TrainingErrorCode.SCHOOL_WRONG_NAME);
            }
            this.name = name;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public Set<Group> getGroups() {
            return groups;
        }

        public void addGroup(Group group) throws TrainingException {
            if (containsGroup(group)) {
                throw new TrainingException(TrainingErrorCode.DUPLICATE_GROUP_NAME);
            }
            groups.add(group);
        }

        public synchronized void removeGroup(Group group) throws TrainingException {
            if (groups.contains(group)) {
                groups.remove(group);
            } else throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }

        public synchronized void removeGroup(String name) throws TrainingException {
            if (!groups.remove(new Group(name, " "))){
                throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
            }
        }

        public boolean containsGroup(Group group) {
            return groups.contains(group);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            School school = (School) o;
            return year == school.year &&
                    Objects.equals(name, school.name) &&
                    Objects.equals(groups, school.groups);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, year, groups);
        }
    }

}
