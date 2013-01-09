package com.tp.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ct")
public class ClientType extends CateItem {

}
