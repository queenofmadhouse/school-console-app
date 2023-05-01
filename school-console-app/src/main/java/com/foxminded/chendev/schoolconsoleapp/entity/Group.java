package com.foxminded.chendev.schoolconsoleapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "groups", schema = "school")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long groupId;

    @Column(name = "group_name")
    private String groupName;

    private Group(Builder builder) {
        this.groupId = builder.groupId;
        this.groupName = builder.groupName;
    }

    protected Group() {

    }


    public static Builder builder() {
        return new Builder();
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
