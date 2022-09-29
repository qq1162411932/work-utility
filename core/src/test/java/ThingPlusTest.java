import org.junit.jupiter.api.Test;
import org.thingsboard.rest.client.RestClient;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.query.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/29 16:13
 */

public class ThingPlusTest {

    RestClient restClient = new RestClient("http://39.101.175.163");

    @Test
    public void testFilter() {
        String username = "tenant@thingsboard.org";
        String pas = "tenant456";

        restClient.login(username, pas);
        EntityTypeFilter typeFilter = new EntityTypeFilter();

        typeFilter.setEntityType(EntityType.DEVICE);
        EntityCountQuery totalDevicesQuery = new EntityCountQuery(typeFilter);
        Long totalDevicesCount = restClient.countEntitiesByQuery(totalDevicesQuery);
        System.out.println(totalDevicesCount);


        KeyFilter keyFilter = getKeyFilter("deviceBigCategory", "安全监测", StringFilterPredicate.StringOperation.EQUAL);
        KeyFilter projectId = getKeyFilter("projectId", "1", StringFilterPredicate.StringOperation.EQUAL);

        List<KeyFilter> keyFilterList = new ArrayList<>();
        keyFilterList.add(keyFilter);
        keyFilterList.add(projectId);

        EntityCountQuery totalActiveDevicesQuery = new EntityCountQuery(typeFilter, keyFilterList);
        Long total = restClient.countEntitiesByQuery(totalActiveDevicesQuery);
        System.out.println(total);
    }

    public KeyFilter getKeyFilter(String key, String value, StringFilterPredicate.StringOperation pattern) {
        KeyFilter keyFilter = new KeyFilter();
        keyFilter.setKey(new EntityKey(EntityKeyType.SHARED_ATTRIBUTE, key));

        keyFilter.setValueType(EntityKeyValueType.STRING);
        StringFilterPredicate stringFilterPredicate = new StringFilterPredicate();
        stringFilterPredicate.setOperation(pattern);
        stringFilterPredicate.setValue(new FilterPredicateValue<>(value));
        keyFilter.setPredicate(stringFilterPredicate);
        return keyFilter;
    }

    @Test
    public void testPageData() {
        String username = "tenant@thingsboard.org";
        String pas = "tenant456";
        restClient.login(username, pas);

        EntityTypeFilter typeFilter = new EntityTypeFilter();
        typeFilter.setEntityType(EntityType.DEVICE);

        KeyFilter keyFilter = getKeyFilter("name", "B", StringFilterPredicate.StringOperation.CONTAINS);

        // Prepare list of queried device fields
        List<EntityKey> fields = new ArrayList() {{
//            new EntityKey(EntityKeyType.ENTITY_FIELD, "name");
//            new EntityKey(EntityKeyType.ENTITY_FIELD, "type");
//            new EntityKey(EntityKeyType.ENTITY_FIELD, "createdTime");
            new EntityKey(EntityKeyType.SHARED_ATTRIBUTE, "deviceBigCategory");
            new EntityKey(EntityKeyType.SHARED_ATTRIBUTE, "deviceCategory");
        }};

        // Prepare list of queried device attributes
        List<EntityKey> attributes = new ArrayList(){{
            new EntityKey(EntityKeyType.SHARED_ATTRIBUTE, "deviceBigCategory");
            new EntityKey(EntityKeyType.SHARED_ATTRIBUTE, "deviceCategory");
        }};

        // Prepare page link
        EntityDataSortOrder sortOrder = new EntityDataSortOrder();
        sortOrder.setKey(new EntityKey(EntityKeyType.ENTITY_FIELD, "createdTime"));
        sortOrder.setDirection(EntityDataSortOrder.Direction.DESC);
        EntityDataPageLink entityDataPageLink = new EntityDataPageLink(10, 0, "", sortOrder);

        // Create entity query with provided entity filter, key filter, queried fields and page link
        EntityDataQuery dataQuery =
                new EntityDataQuery(typeFilter, entityDataPageLink, fields, attributes, new ArrayList(){{add(keyFilter);}});

        PageData<EntityData> entityPageData;
        do {
            // Fetch active devices using entities query and print them
            entityPageData = restClient.findEntityDataByQuery(dataQuery);
            restClient.findByQuery()
            entityPageData.getData().forEach(System.out::println);
            dataQuery = dataQuery.next();
        } while (entityPageData.hasNext());

    }
}
