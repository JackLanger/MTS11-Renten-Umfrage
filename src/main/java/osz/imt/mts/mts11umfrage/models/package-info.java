/**
 * Package containing all tables managed by Hibernate.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 20.10.2022</p>
 *
 * @author Jacek Langer
 */

@org.hibernate.annotations.GenericGenerator(
    name = "UUID",
    strategy = "org.hibernate.id.UUIDGenerator"
)
package osz.imt.mts.mts11umfrage.models;