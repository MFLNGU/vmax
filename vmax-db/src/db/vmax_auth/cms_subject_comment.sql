drop table if exists cms_subject_comment;

/*==============================================================*/
/* Table: cms_subject_comment                                   */
/*==============================================================*/
create table cms_subject_comment
(
   id                   bigint not null auto_increment,
   subject_id           bigint,
   member_nick_name     varchar(255),
   member_icon          varchar(255),
   content              varchar(1000),
   create_time          datetime,
   show_status          int(1),
   primary key (id)
);

alter table cms_subject_comment comment '专题评论表';

alter table cms_subject_comment add constraint FK_Reference_29 foreign key (subject_id)
      references cms_subject (id) on delete restrict on update restrict;
