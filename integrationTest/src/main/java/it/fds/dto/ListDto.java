package it.fds.dto;

import java.sql.Timestamp;

public class ListDto {
    private String uuid;
    private Timestamp createdat;
    private Timestamp updatedat;
    private Timestamp duedate;
    private Timestamp resolvedat;
    private Timestamp postponedat;
    private String postponedtime;
    private String title;
    private String description;
    private String priority;
    private String status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    public Timestamp getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Timestamp updatedat) {
        this.updatedat = updatedat;
    }

    public Timestamp getDuedate() {
        return duedate;
    }

    public void setDuedate(Timestamp duedate) {
        this.duedate = duedate;
    }

    public Timestamp getResolvedat() {
        return resolvedat;
    }

    public void setResolvedat(Timestamp resolvedat) {
        this.resolvedat = resolvedat;
    }

    public Timestamp getPostponedat() {
        return postponedat;
    }

    public void setPostponedat(Timestamp postponedat) {
        this.postponedat = postponedat;
    }

    public String getPostponedtime() {
        return postponedtime;
    }

    public void setPostponedtime(String postponedtime) {
        this.postponedtime = postponedtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
