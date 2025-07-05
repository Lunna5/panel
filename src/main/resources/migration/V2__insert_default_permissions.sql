-- V1__seed_permissions.sql

-- Admin Permissions
INSERT INTO `permissions` (`name`, `description`)
VALUES
    ('admin.users.manage.list', 'Allows listing and viewing users'),
    ('admin.users.manage.view', 'Allows viewing user by email or username'),
    ('admin.users.manage.create', 'Allows creating new users'),
    ('admin.users.manage.update', 'Allows updating user data and enabling/disabling 2FA'),
    ('admin.users.manage.delete', 'Allows deleting users'),
    ('admin.users.manage.roles', 'Allows assigning or removing roles from users'),
    ('admin.users.manage.update.password', 'Allows changing user password')
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Roles

INSERT INTO `roles` (`name`, `description`)
VALUES
    ('admin', 'Administrator role with full access to all features'),
    ('user', 'Standard user role with limited access to features')
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Roles <-> Permissions Relationships
INSERT INTO `role_permissions` (`role_id`, `permission_id`)
SELECT r.id, p.id
FROM `roles` r
JOIN `permissions` p ON p.name IN (
    'admin.users.manage.list',
    'admin.users.manage.view',
    'admin.users.manage.create',
    'admin.users.manage.update',
    'admin.users.manage.delete',
    'admin.users.manage.roles',
    'admin.users.manage.update.password'
)
WHERE r.name = 'admin';
