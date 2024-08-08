package server.tigglemate.domain.account.dto;

import lombok.Getter;
import lombok.Setter;
import server.tigglemate.domain.account.domain.entity.AccountCategory;

@Getter
@Setter
public class CategoryCountDTO {
    private AccountCategory category;
    private int count;
}
