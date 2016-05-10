var gulp = require('gulp'),
    browserify = require('gulp-browserify'),
    browserSync = require('browser-sync'),
    less = require('gulp-less');

var path = {
    js: {
        src: 'assets/javascripts/',
        dest: 'target/web/public/main/javascripts/'
    },
    css: {
        src: 'assets/stylesheets/',
        dest: 'target/web/public/main/stylesheets/'
    },
    html: {
        src: 'app/views/'
    }
};

gulp.task('browserify', function() {
    gulp.src(path.js.src + 'main.js')
        .pipe(gulp.dest(path.js.dest));
});

gulp.task('less', function () {
    return gulp.src(path.css.src + '**/*.less')
        .pipe(less())
        .pipe(gulp.dest(path.css.dest))
        .pipe(browserSync.reload({stream:true}));;
});

gulp.task('watch', function() {
    gulp.watch(path.js.src + '**/*.js', [ 'browserify', browserSync.reload ]);
    gulp.watch(path.css.src + '**/*.less', [ 'less' ]);
});

gulp.task('serve', function () {
    browserSync({
        proxy: 'localhost:9000',
        port: 9001,
        files: [
            'app/views/*.html'
        ],
        open: false,
        ghostMode: false
    });
});

gulp.task('default', ['less', 'browserify', 'watch', 'serve']);