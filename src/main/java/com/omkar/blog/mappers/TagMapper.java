package com.omkar.blog.mappers;

import com.omkar.blog.domain.PostStatus;
import com.omkar.blog.domain.dtos.TagDto;
import com.omkar.blog.domain.entities.Post;
import com.omkar.blog.domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    TagDto toTagDto(Tag tag);

    @Named("calculatePostCount")
    default Integer calculatePostCount(Set<Post> posts) {
        if (posts == null) return 0;
        return (int) posts.stream()
                .filter(post -> PostStatus.PUBLISHED.equals(post.getPostStatus()))
                .count();
    }
}
