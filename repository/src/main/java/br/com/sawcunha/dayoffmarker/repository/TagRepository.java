package br.com.sawcunha.dayoffmarker.repository;

import br.com.sawcunha.dayoffmarker.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

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
	boolean existsByCodeAndNotId(String code, Long tagId);

	@Query("""
			SELECT count(t) = :total
			FROM Tag t
			WHERE t.id IN :tags
			""")
	boolean existsByTags(Long total, Set<Long> tags);

}
