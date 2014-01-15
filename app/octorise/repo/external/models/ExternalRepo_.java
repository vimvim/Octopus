package octorise.repo.external.models;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 */
@StaticMetamodel(ExternalRepo.class)
public class ExternalRepo_ {
    public static volatile SingularAttribute<ExternalRepo, Integer> id;
    public static volatile SingularAttribute<ExternalRepo, String> name;
}
