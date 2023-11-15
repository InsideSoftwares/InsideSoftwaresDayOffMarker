package br.com.insidesoftwares.dayoffmarker;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.sort.PropertiesOrder;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public abstract class RepositoryTestUtils {

    protected static final UUID COUNTRY_BRS_ID = UUID.fromString("2596e281-4dc1-40bf-afb5-08b245de54a6");
    protected static final UUID STATE_MG_ID = UUID.fromString("938b4593-6eb5-4a3c-a504-eacaca04d893");
    protected static final UUID CITY_BARB_ID = UUID.fromString("b96fd571-e370-4d6a-a59b-cba58f93e980");
    protected static final UUID CITY_MTCS_ID = UUID.fromString("4d2925fe-f58c-4821-accf-bd67b3b7a58d");
    protected static final UUID CITY_VESP_ID = UUID.fromString("9f6507c5-f31f-4512-ac71-7ed708eeea42");
    protected static final UUID STATE_SP_ID = UUID.fromString("65af072d-dcbe-47be-b7a8-c54a57ab43dd");
    protected static final UUID CITY_EBAR_ID = UUID.fromString("bea4a208-cd61-424c-b5e9-d5a93d9f39bd");
    protected static final UUID CITY_AGLD_ID = UUID.fromString("c15b8719-a711-4687-8af0-dbf56625c17d");
    protected static final UUID CITY_BOTU_ID = UUID.fromString("b04962ba-fbd7-4974-b921-df21c4cd507b");
    protected static final UUID STATE_RJ_ID = UUID.fromString("f7485292-7c4d-48c8-b46e-00efda156c45");
    protected static final UUID CITY_BEXO_ID = UUID.fromString("97832307-11ed-4da8-8812-adb4c322f697");
    protected static final UUID CITY_PELIS_ID = UUID.fromString("f30fe5bf-e14e-44d3-b4bb-be80eaa1fc59");
    protected static final UUID CITY_TELIS_ID = UUID.fromString("9fbf986e-3abe-4c87-aadf-65280fbcc48d");
    protected static final UUID COUNTRY_EUA_ID = UUID.fromString("f79fe4a0-707f-4c51-a052-efac3e6b5d2e");
    protected static final UUID STATE_TEXAS_ID = UUID.fromString("bf495081-7eca-4dab-9083-64a49d4273be");
    protected static final UUID CITY_AUIN_ID = UUID.fromString("c7a9a1e4-9d6e-46fc-8e58-758ac901e3b9");
    protected static final UUID CITY_TXCT_ID = UUID.fromString("5e2beefd-ecec-454c-a8bd-dbdc4c85b923");
    protected static final UUID CITY_BROW_ID = UUID.fromString("c4a8d3c5-d15c-46d5-b48a-47a5de651965");
    protected static final UUID STATE_OHIO_ID = UUID.fromString("cd229a54-2b2e-4955-9138-8b7854398d5a");
    protected static final UUID CITY_CITI_ID = UUID.fromString("72f47d56-b89a-40b7-b6c0-d37134ec8da1");
    protected static final UUID CITY_TOLE_ID = UUID.fromString("2e94e812-7a1f-445a-a8a7-3aeecdef67ef");
    protected static final UUID CITY_CHITH_ID = UUID.fromString("469938e2-7226-4ada-85c4-cce29068fa40");
    protected static final UUID STATE_OKLAHOMA_ID = UUID.fromString("5379de3a-15e8-41c1-a09d-09324f0fec47");
    protected static final UUID CITY_TUL_ID = UUID.fromString("b7066ed0-bbb8-4b16-a224-fc6ce74a3eae");
    protected static final UUID CITY_BRAW_ID = UUID.fromString("c3a47d8a-2895-49d4-b162-e14f81d6da30");
    protected static final UUID CITY_YUKN_ID = UUID.fromString("003b2f48-c279-42a3-b707-4772f41e45d6");
    protected static final String CITY_NAME_BARBACENA = "Barbacena";
    protected static final String CITY_CODE_BARB01 = "BARB01";
    protected static final String CITY_ACRONYM_BARB = "BARB";
    protected static final String CITY_ACRONYM_PELIS = "PELIS";
    protected static final UUID CITY_ID_INVALID = UUID.randomUUID();
    protected static final UUID COUNTRY_ID_INVALID = UUID.randomUUID();
    protected static final UUID FIXED_HOLIDAY_1_1_ID = UUID.fromString("966eb9f5-36f8-4e38-a063-8765c2690ad6");
    protected static final UUID FIXED_HOLIDAY_2_11_ID = UUID.fromString("076e8824-3d1e-493b-a58d-84d2c1f1ed0e");
    protected static final UUID FIXED_HOLIDAY_ID_INVALID = UUID.randomUUID();
    protected static final UUID DAY_ID_11_11_23 = UUID.fromString("ebf9265f-f2c3-4efa-9e75-6473810f7bec");
    protected static final UUID DAY_ID_INVALID = UUID.randomUUID();
    protected static final UUID TAG_ID_AKLA = UUID.fromString("89533fcc-6b11-42a6-8038-72c7e3ce2796");
    protected static final UUID TAG_ID_INVALID = UUID.randomUUID();


    public Pageable createPageable(
            final Integer page,
            final Integer sizePerpage,
            final Sort.Direction direction,
            final String order
    ) {
        InsidePaginationFilterDTO insidePaginationFilterDTO = InsidePaginationFilterDTO.builder()
                .page(page)
                .sizePerPage(sizePerpage)
                .direction(direction)
                .order(order)
                .build();

        return PaginationUtils.createPageable(insidePaginationFilterDTO, eOrderCity.ID);
    }

    @AllArgsConstructor
    @Getter
    public enum eOrderCity implements PropertiesOrder {
        ID("id"),
        NAME("name"),
        ACRONYM("acronym"),
        CODE("code"),
        STATE_NAME("state.name"),
        STATE_ACRONYM("state.acronym");

        private final String properties;

        @Override
        public String properties() {
            return this.getProperties();
        }

        @Override
        public String value(String name) {
            try {
                return br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCity.valueOf(name).getProperties();
            } catch (Exception exception) {
                return this.getProperties();
            }
        }
    }
}
