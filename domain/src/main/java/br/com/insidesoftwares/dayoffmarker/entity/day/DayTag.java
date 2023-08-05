package br.com.insidesoftwares.dayoffmarker.entity.day;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DOM_DAY_TAG")
public class DayTag {

	@Id
	@Column(name="DAY_ID")
	private Long dayID;
	@Column(name="TAG_ID")
	private Long tagID;

}
