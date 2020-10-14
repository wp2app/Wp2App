package cn.wp2app.xmlrpc.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cn.wp2app.xmlrpc.enums.CommentStatus;
import cn.wp2app.xmlrpc.enums.PingStatus;
import cn.wp2app.xmlrpc.enums.PostStatus;
import cn.wp2app.xmlrpc.model.interval.Enclosure;

import java.util.Date;
import java.util.List;

/**
 * @author robin
 */
public class Post {

    @JsonProperty("post_id")
    private Integer postId;

    @JsonProperty("post_title")
    private String postTitle;

    @JsonProperty("post_date_gmt")
    private Date postDateGmt;

    @JsonProperty("post_modified")
    private Date postModified;

    @JsonProperty("post_modified_gmt")
    private Date postModifiedGmt;

    @JsonProperty("post_status")
    private PostStatus postStatus;

    @JsonProperty("post_type")
    private String postType;

    @JsonProperty("post_format")
    private String postFormat;

    @JsonProperty("post_name")
    private String postName;

    @JsonProperty("post_author")
    private String postAuthor;

    @JsonProperty("post_password")
    private String postPassword;

    @JsonProperty("post_excerpt")
    private String postExcerpt;

    @JsonProperty("post_content")
    private String postContent;

    @JsonProperty("post_parent")
    private String postParent;

    @JsonProperty("post_mime_type")
    private String postMimeType;

    private String link;

    private String guid;

    @JsonProperty("menu_order")
    private int menuOrder;

    @JsonProperty("comment_status")
    private CommentStatus commentStatus;

    @JsonProperty("ping_status")
    private PingStatus pingStatus;

    private boolean sticky;

    @JsonProperty("post_thumbnail")
    private List<MediaItem> postThumbnail;

    private List<Term> terms;

    @JsonProperty("custom_fields")
    private List<CustomField> customFields;

    private Enclosure enclosure;


    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public Date getPostDateGmt() {
        return postDateGmt;
    }

    public void setPostDateGmt(Date postDateGmt) {
        this.postDateGmt = postDateGmt;
    }

    public Date getPostModified() {
        return postModified;
    }

    public void setPostModified(Date postModified) {
        this.postModified = postModified;
    }

    public Date getPostModifiedGmt() {
        return postModifiedGmt;
    }

    public void setPostModifiedGmt(Date postModifiedGmt) {
        this.postModifiedGmt = postModifiedGmt;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getPostFormat() {
        return postFormat;
    }

    public void setPostFormat(String postFormat) {
        this.postFormat = postFormat;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }

    public String getPostPassword() {
        return postPassword;
    }

    public void setPostPassword(String postPassword) {
        this.postPassword = postPassword;
    }

    public String getPostExcerpt() {
        return postExcerpt;
    }

    public void setPostExcerpt(String postExcerpt) {
        this.postExcerpt = postExcerpt;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostParent() {
        return postParent;
    }

    public void setPostParent(String postParent) {
        this.postParent = postParent;
    }

    public String getPostMimeType() {
        return postMimeType;
    }

    public void setPostMimeType(String postMimeType) {
        this.postMimeType = postMimeType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }

    public PingStatus getPingStatus() {
        return pingStatus;
    }

    public void setPingStatus(PingStatus pingStatus) {
        this.pingStatus = pingStatus;
    }

    public boolean isSticky() {
        return sticky;
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    public List<MediaItem> getPostThumbnail() {
        return postThumbnail;
    }

    public void setPostThumbnail(List<MediaItem> postThumbnail) {
        this.postThumbnail = postThumbnail;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public List<CustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }


    public static class CustomField {

        private String id;

        private String key;

        private String value;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
