package com.foxminded.chendev.schoolconsoleapp.entity;

public class Group {

    private long groupID;
    private String groupName;

    private Group(Builder builder) {
        this.groupID = builder.groupID;
        this.groupName = builder.groupName;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupID=" + groupID +
                ", groupName='" + groupName + '\'' +
                '}';
    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private long groupID;
        private String groupName;

        public Builder withGroupID(long groupID) {
            this.groupID = groupID;
            return this;
        }

        public Builder withGroupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public Group build() {
            return new Group(this);
        }
    }
}
