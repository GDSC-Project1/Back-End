package server.tigglemate_pre.domain.account.dto;

import lombok.Getter;
import lombok.Setter;
import server.tigglemate_pre.domain.account.domian.entity.AccountCategory;

@Getter
@Setter
public class CategoryCountDTO {
    private AccountCategory category;
    private int count;
}
