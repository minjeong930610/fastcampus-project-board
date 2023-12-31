package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //작성자

    @Setter @Column(nullable = false) private String title; //제목
    @Setter @Column(nullable = false, length = 100000) private String content; //내용

    @Setter private String hashtag; //해시태그

    //양방향 바인딩
    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    //전체 setter를 걸지 않는 이유 : 자동으로 jpa가 세팅하도록 만들 것이기 때문에
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; //생성일시
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; //생성자
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; //수정일시
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; //수정자

    //기본생성자 생성
    protected Article() {}

    //private로 막아버리고 팩토리 메소드를 통해서 제공할 수 있도록 하겠음
    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    //가이드가 되도록 작성
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
