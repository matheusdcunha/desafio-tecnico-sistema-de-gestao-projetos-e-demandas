ALTER TABLE tasks
DROP CONSTRAINT IF EXISTS fksfhn82y57i3k9uxww1s007acc;

ALTER TABLE tasks
    ADD CONSTRAINT fk_tasks_project
        FOREIGN KEY (project_id)
            REFERENCES projects(id)
            ON DELETE CASCADE;