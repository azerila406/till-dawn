package com.tilldawn.model.user;

import com.tilldawn.model.Assets;

public class User {
    private String username;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private int score = 0;
    private int kills = 0;
    private int longestTime = 0;
    private String path = Assets.getRandomAvatar();

    public String getPath() {
        return path;
    }

    public void update() {
        UserRepository.getInstance().updateUser(this);
    }

    public void setPath(String path) {
        this.path = path;
    }


    public User(String username, String password, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() { return securityQuestion; }
    public void setSecurityQuestion(String securityQuestion) { this.securityQuestion = securityQuestion; }

    public String getSecurityAnswer() { return securityAnswer; }
    public void setSecurityAnswer(String securityAnswer) { this.securityAnswer = securityAnswer; }

    public int getScore() {
        return score;
    }

    public void increaseScore(int amount) {
        score += amount;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void increaseKills(int amount) {
        kills += amount;
    }

    public int getLongestTime() {
        return longestTime;
    }

    public void setLongestTime(int longestTime) {
        this.longestTime = Math.max(longestTime, this.longestTime);
    }
}

