package cn.wp2app.xmlrpc;

import cn.wp2app.xmlrpc.exception.WPClientException;
import cn.wp2app.xmlrpc.model.request.PostFilter;
import cn.wp2app.xmlrpc.model.request.PostRequest;
import cn.wp2app.xmlrpc.model.response.Author;
import cn.wp2app.xmlrpc.model.response.Post;
import cn.wp2app.xmlrpc.model.response.UserBlog;

import java.util.List;

/**
 * @author robin
 */
public final class WordPress {

    private WPClient client;

    public WordPress(WPConfig config) {
        try {
            client = new WPClient(config);
        } catch (Exception e) {
            throw new WPClientException("Error in creating wp client.", e);
        }
    }

    public WordPress(String xmlRpcUrl, String username, String password) {
        WPConfig config = new WPConfigBuilder()
                .xmlRpcUrl(xmlRpcUrl)
                .username(username)
                .password(password)
                .trustAll(true)
                .build();
        try {
            client = new WPClient(config);
        } catch (Exception e) {
            throw new WPClientException("Error in creating wp client.", e);
        }
    }


    public UserBlog getUserBlog() {
        List<UserBlog> list = getUsersBlogs();
        if (null == list || list.isEmpty()) {
            throw new WPClientException("Cannot get users and blogs.");
        }
        return list.get(0);
    }


    public List<UserBlog> getUsersBlogs() {
        try {
            return client.getUsersBlogs();
        } catch (Exception e) {
            throw new WPClientException("Error in getting users and blogs.", e);
        }
    }


    public Author getAuthor() {
        List<Author> list = getAuthors();
        if (null == list || list.isEmpty()) {
            throw new WPClientException("Cannot get authors.");
        }
        return list.get(0);
    }


    public List<Author> getAuthors() {
        try {
            return client.getAuthors();
        } catch (Exception e) {
            throw new WPClientException("Error in getting authors.", e);
        }
    }


    public List<Post> getPosts(PostFilter filter, String... fields) {
        try {
            return client.getPosts(filter, fields);
        } catch (Exception e) {
            throw new WPClientException("Error in getting posts.", e);
        }
    }


    public Post getPost(int postId, String... fields) {
        try {
            return client.getPost(postId, fields);
        } catch (Exception e) {
            throw new WPClientException("Error in getting posts.", e);
        }
    }


    public int newPost(PostRequest post) {
        try {
            return client.newPost(post);
        } catch (Exception e) {
            throw new WPClientException("Creating new post failed.", e);
        }
    }


    public boolean editPost(int postId, PostRequest post) {
        try {
            return client.editPost(postId, post);
        } catch (Exception e) {
            throw new WPClientException("Editing post with id " + postId + " failed.", e);
        }
    }


    public boolean deletePost(int postId) {
        try {
            return client.deletePost(postId);
        } catch (Exception e) {
            throw new WPClientException("Deleting post with id " + postId + " failed.", e);
        }
    }
}
