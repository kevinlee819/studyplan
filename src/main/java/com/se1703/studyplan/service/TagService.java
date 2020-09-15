package com.se1703.studyplan.service;

import com.se1703.studyplan.entity.Tag;
import com.se1703.studyplan.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author leekejin
 * @date 2020/9/15 16:35
 **/
@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    /**
     * 根据用户id获取该用户独有的Tag
     * @param userId
     * @return
     */
    public List<Tag> getSelfTag(String userId){
        return tagMapper.findByUserId(userId);
    }

    public static Set<String> getStringTags(List<Tag> tags){
        if (tags == null || tags.isEmpty()){
            return null;
        }
        HashSet<String> tagSet = new HashSet<>();
        for (Tag tag: tags) {
            tagSet.add(tag.getTagName());
        }
        return tagSet;
    }


    /**
     * 返回用户可以看得见的tag
     * @param userId
     * @return 用户可以看得见的tag
     */
    public List<Tag> getUserTag(String userId){
        List<Tag> selfTags = getSelfTag(userId);
        List<Tag> commonTags = getCommonTag();
        if (selfTags != null){
            if (commonTags != null && !commonTags.isEmpty()){
                for (Tag e : commonTags) {
                    selfTags.add(e);
                }
            }
        }
        return selfTags;
    }

    /**
     * 返回共有标签
     * @return 共有标签
     */
    public List<Tag> getCommonTag(){
        return tagMapper.findByUserId("common");
    }

    /**
     * 新建共有标签
     * @param tagName
     * @return
     */
    public String createCommonTag(String tagName){
        Tag tag = new Tag();
        tag.setTagName(tagName);
        tag.setUserId("common");
        return tagMapper.saveTag(tag);
    }

    public String saveTag(Tag tag){
        return tagMapper.saveTag(tag);
    }

    public boolean delCommonTagByName(String name){
        return tagMapper.deleteByUserIdAndName("common",name);
    }

}
