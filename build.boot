(set-env!
  :resource-paths #{"src"}
  :wagons '[[s3-wagon-private "1.2.0"]]
  :repositories [["clojars" "http://clojars.org/repo/"]
                 ["maven-central" "http://repo1.maven.org/maven2/"]
                 ["provisdom" {:url        "s3p://provisdom-artifacts/releases/"
                               :username   (System/getenv "AWS_ACCESS_KEY")
                               :passphrase (System/getenv "AWS_SECRET_KEY")}]]

  :dependencies '[[provisdom/boot-tasks "0.4.1" :scope "test" :exclusions [commons-codec]]])

(require
  '[provisdom.boot-tasks :refer :all])

(set-project-deps!)
(default-task-options!)

(deftask release2
         "Developer workflow for web-component UX."
         [u access-key VALUE str "Username"
          p secret-key VALUE str "Password"]
         (comp
           (pom)
           (jar)
           (push :repo-map {:url        "s3p://provisdom-artifacts/releases/"
                            :username   access-key
                            :passphrase secret-key})))