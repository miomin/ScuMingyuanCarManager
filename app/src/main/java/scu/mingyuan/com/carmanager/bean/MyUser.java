package scu.mingyuan.com.carmanager.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by miomin on 16/3/1.
 */
public class MyUser extends BmobUser {

    private Boolean sex; // 男true 女false
    private String nick; // 昵称
    private Integer age; // 年龄

    public MyUser(String nick) {
        super();
        sex = true;
        this.nick = nick;
        age = 0;
    }

    public boolean isMale() {
        return this.sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "age=" + age +
                ", sex=" + sex +
                ", nick='" + nick + '\'' +
                '}' + super.toString();
    }
}
