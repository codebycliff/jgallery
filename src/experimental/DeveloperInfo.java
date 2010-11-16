package experimental;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;
import runtime.Application;
import runtime.Boot;


public class DeveloperInfo {

    public static final String NAME_KEY = "Name";
    public static final String EMAIL_KEY = "Email";
    public static final String HOBBIES_KEY = "Hobbies";
    public static final String UNIVERSITY_KEY = "University";
    public static final String UNIVERSITY_GRADELEVEL_KEY = "UniversityGradeLevel";
    public static final String UNIVERSITY_MAJOR_KEY = "UniversityMajor";
    public static final String DEVELOPMENT_INTERESTS_KEY = "DevelopmentInterests";
    public static final String IMAGE_KEY = "Image";
    public static final String ABOUTME_KEY = "AboutMe";

    public DeveloperInfo() {
        mHobbies = new LinkedList<String>();
        mDevelopmentInterests = new LinkedList<String>();
        mEmailAddresses = new LinkedList<String>();
    }

    public DeveloperInfo(String name, String aboutMeText, Image image) {

    }

    public String getName() {
        return mName;
    }

    public List<String> getEmails() {
        return mEmailAddresses;
    }

    public List<String> getHobbies() {
        return mHobbies;
    }

    public List<String> getDevelopmentInterests() {
        return mDevelopmentInterests;
    }

    public String getUniversity() {
        return mUniversityName;
    }

    public String getUniversityGradeLevel() {
        return mUniversityGradeLevel;
    }

    public String getUniversityMajor() {
        return mUniversityMajor;
    }

    public Image getImage() {
        return mPersonalImage;
    }

    public String getAboutMe() {
        return mAboutMeText;
    }

    public void setName(String name) {
        mName = name;
    }

    public void addEmail(String email) {
        mEmailAddresses.add(email);
    }

    public void addHobby(String hobby) {
        mHobbies.add(hobby);
    }

    public void addDevelopmentInterest(String interest) {
        mDevelopmentInterests.add(interest);
    }

    public void setUniversityName(String university) {
        mUniversityName = university;
    }

    public void setUniversityGradeLevel(String grade) {
        mUniversityGradeLevel = grade;
    }

    public void setUniversityMajor(String major) {
        mUniversityMajor = major;
    }

    public void setAboutMeText(String desc) {
        mAboutMeText = desc;
    }

    public void setImage(Image image) {
        mPersonalImage = image;
    }

    public static DeveloperInfo load(File file) {
        
        if (file.exists()) {
            DeveloperInfo info = new DeveloperInfo();
            Scanner scanner;
            try {
                scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] keyValuePair = line.split("=");
                    String key = keyValuePair[0];
                    String[] values;
                    if (keyValuePair[1].contains(",")) {
                        values = keyValuePair[1].split(",");
                    }
                    else {
                        values = new String[] { keyValuePair[1] };
                    }

                    
                    if (key.compareTo(NAME_KEY) == 0) {
                        info.setName(values[0]);
                    }
                    else if (key.compareTo(EMAIL_KEY) == 0) {
                        for (int i = 0; i < values.length - 1; i++) {
                            String email = values[i];
                            if (email.contains("|")) {
                                email = email.replace("|", " (");
                                email += ")";
                                info.addEmail(email);
                            }
                        }
                    }
                    else if (key.compareTo(HOBBIES_KEY) == 0) {
                        for (int i = 0; i < values.length - 1; i++) {
                            info.addHobby(values[i]);
                        }
                    }
                    else if (key.compareTo(DEVELOPMENT_INTERESTS_KEY) == 0) {
                        for (int i = 0; i < values.length - 1; i++) {
                            info.addDevelopmentInterest(values[i]);
                        }
                    }
                    else if (key.compareTo(ABOUTME_KEY) == 0) {
                        info.setAboutMeText(values[0]);
                    }
                    else if (key.compareTo(UNIVERSITY_KEY) == 0) {
                        info.setUniversityName(values[0]);
                    }
                    else if (key.compareTo(UNIVERSITY_GRADELEVEL_KEY) == 0) {
                        info.setUniversityGradeLevel(values[0]);
                    }
                    else if (key.compareTo(UNIVERSITY_MAJOR_KEY) == 0) {
                        info.setUniversityMajor(values[0]);
                    }
                    else if (key.compareTo(IMAGE_KEY) == 0) {
                        String path = "resources/about/developer/" + values[0];
                        URL url = Boot.class.getResource(path);
                        if(url != null) {
                            File f = new File(url.getPath());
                            Image image = null;
                            try {
                                image =ImageIO.read(f);
                            } catch(Exception e) {
                                Application.dump(e);
                            }
                            if(image != null) {
                                info.setImage(image);
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                Application.dump(e);
            }
            return info;
        }
        return null;
    }
    
    private String mName;
    private String mUniversityName;
    private String mUniversityMajor;
    private String mUniversityGradeLevel;
    private List<String> mEmailAddresses;
    private List<String> mHobbies;
    private List<String> mDevelopmentInterests;
    private Image mPersonalImage;
    private String mAboutMeText;
}
