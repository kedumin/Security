package com.xiaobin.security.domain;

/**
 * Created with IntelliJ IDEA.
 * User: kedumin
 * Date: 14-2-2
 * Time: 下午11:02
 * To change this template use File | Settings | File Templates.
 */
public class UpdateInfo {

    private String version;
    private String description;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
