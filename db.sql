USE inno_share;

CREATE TABLE users
(
    user_id     INT PRIMARY KEY AUTO_INCREMENT,
    username    VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    is_verified BOOLEAN      NOT NULL DEFAULT FALSE,
    salt        VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE papers
(
    paper_id     INT PRIMARY KEY AUTO_INCREMENT,
    user_id      INT NOT NULL,
    title        VARCHAR(255) NOT NULL,
    author       VARCHAR(255) NOT NULL,
    abstract     TEXT,
    keywords     TEXT,
    file_path    VARCHAR(255) NOT NULL,
    published_at DATE,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE download
(
    download_id   INT PRIMARY KEY AUTO_INCREMENT,
    user_id       INT NOT NULL,
    paper_id      INT NOT NULL,
    download_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (paper_id) REFERENCES papers (paper_id)
);

CREATE TABLE browse
(
    browse_id   INT PRIMARY KEY AUTO_INCREMENT,
    user_id     INT NOT NULL,
    paper_id    INT NOT NULL,
    browse_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (paper_id) REFERENCES papers (paper_id)
);

CREATE TABLE statistic
(
    stat_id        INT PRIMARY KEY AUTO_INCREMENT,
    paper_id       INT NOT NULL,
    view_count     INT DEFAULT 0,
    download_count INT DEFAULT 0,
    created_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (paper_id) REFERENCES papers (paper_id)
);

CREATE TABLE paper_references
(
    reference_id    INT PRIMARY KEY AUTO_INCREMENT,
    citing_paper_id INT NOT NULL,
    cited_paper_id  INT NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (citing_paper_id) REFERENCES papers (paper_id),
    FOREIGN KEY (cited_paper_id) REFERENCES papers (paper_id)
);
