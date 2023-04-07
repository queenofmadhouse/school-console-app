package com.foxminded.chendev.schoolconsoleapp.entity;

public class Group {

    private long groupId;
    private String groupName;

    private Group(Builder builder) {
        this.groupId = builder.groupId;
        this.groupName = builder.groupName;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupID=" + groupId +
                ", groupName='" + groupName + '\'' +
                '}';
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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

        private long groupId;
        private String groupName;

        public Builder withGroupId(long groupID) {
            this.groupId = groupID;
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
