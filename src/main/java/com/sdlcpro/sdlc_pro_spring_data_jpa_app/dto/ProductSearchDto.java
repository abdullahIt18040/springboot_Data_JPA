package com.sdlcpro.sdlc_pro_spring_data_jpa_app.dto;

import java.util.List;

public record ProductSearchDto (String searchValue,
                                String category,

                                double minPrice,
                                double maxprice,
                                List<String>brands


                                ){
}
