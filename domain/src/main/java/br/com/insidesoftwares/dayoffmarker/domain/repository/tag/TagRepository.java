package br.com.insidesoftwares.dayoffmarker.domain.repository.tag;

import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query("""
            SELECT count(t)>0
            FROM Tag t
            WHERE LOWER(t.code) = LOWER(:code)
            """)
    boolean existsByCode(String code);

    @Query("""
            SELECT count(t)>0
            FROM Tag t
            WHERE LOWER(t.code) = LOWER(:code) AND
            t.id != :tagId
            """)
    boolean existsByCodeAndNotId(String code, UUID tagId);

    @Query("""
            SELECT count(t) = :total
            FROM Tag t
            WHERE t.id IN :tags
            """)
    boolean existsByTags(Long total, Set<UUID> tags);

}
