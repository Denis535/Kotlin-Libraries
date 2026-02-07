#----------------------------------------------------------------
# Generated CMake target import file for configuration "Release".
#----------------------------------------------------------------

# Commands may need to know the format version.
set(CMAKE_IMPORT_FILE_VERSION 1)

# Import target "SDL3_image::SDL3_image-shared" for configuration "Release"
set_property(TARGET SDL3_image::SDL3_image-shared APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(SDL3_image::SDL3_image-shared PROPERTIES
  IMPORTED_LINK_DEPENDENT_LIBRARIES_RELEASE "SDL3::SDL3-shared"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libSDL3_image.so.0.4.0"
  IMPORTED_SONAME_RELEASE "libSDL3_image.so.0"
  )

list(APPEND _cmake_import_check_targets SDL3_image::SDL3_image-shared )
list(APPEND _cmake_import_check_files_for_SDL3_image::SDL3_image-shared "${_IMPORT_PREFIX}/lib/libSDL3_image.so.0.4.0" )

# Import target "SDL3_image::dav1d" for configuration "Release"
set_property(TARGET SDL3_image::dav1d APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(SDL3_image::dav1d PROPERTIES
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libdav1d.so.6.9.0"
  IMPORTED_SONAME_RELEASE "libdav1d.so.6"
  )

list(APPEND _cmake_import_check_targets SDL3_image::dav1d )
list(APPEND _cmake_import_check_files_for_SDL3_image::dav1d "${_IMPORT_PREFIX}/lib/libdav1d.so.6.9.0" )

# Import target "SDL3_image::aom" for configuration "Release"
set_property(TARGET SDL3_image::aom APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(SDL3_image::aom PROPERTIES
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libaom.so.3.6.1"
  IMPORTED_SONAME_RELEASE "libaom.so.3"
  )

list(APPEND _cmake_import_check_targets SDL3_image::aom )
list(APPEND _cmake_import_check_files_for_SDL3_image::aom "${_IMPORT_PREFIX}/lib/libaom.so.3.6.1" )

# Import target "SDL3_image::external_libavif" for configuration "Release"
set_property(TARGET SDL3_image::external_libavif APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(SDL3_image::external_libavif PROPERTIES
  IMPORTED_LINK_DEPENDENT_LIBRARIES_RELEASE "SDL3_image::dav1d;SDL3_image::aom"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libavif.so.16.0.4"
  IMPORTED_SONAME_RELEASE "libavif.so.16"
  )

list(APPEND _cmake_import_check_targets SDL3_image::external_libavif )
list(APPEND _cmake_import_check_files_for_SDL3_image::external_libavif "${_IMPORT_PREFIX}/lib/libavif.so.16.0.4" )

# Import target "SDL3_image::external_libpng" for configuration "Release"
set_property(TARGET SDL3_image::external_libpng APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(SDL3_image::external_libpng PROPERTIES
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libpng16.so.16.54.0"
  IMPORTED_SONAME_RELEASE "libpng16.so.16"
  )

list(APPEND _cmake_import_check_targets SDL3_image::external_libpng )
list(APPEND _cmake_import_check_files_for_SDL3_image::external_libpng "${_IMPORT_PREFIX}/lib/libpng16.so.16.54.0" )

# Import target "SDL3_image::external_libtiff" for configuration "Release"
set_property(TARGET SDL3_image::external_libtiff APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(SDL3_image::external_libtiff PROPERTIES
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libtiff.so.6.2.0"
  IMPORTED_SONAME_RELEASE "libtiff.so.6"
  )

list(APPEND _cmake_import_check_targets SDL3_image::external_libtiff )
list(APPEND _cmake_import_check_files_for_SDL3_image::external_libtiff "${_IMPORT_PREFIX}/lib/libtiff.so.6.2.0" )

# Import target "SDL3_image::external_libwebp" for configuration "Release"
set_property(TARGET SDL3_image::external_libwebp APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(SDL3_image::external_libwebp PROPERTIES
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libwebp.so.7.1.8"
  IMPORTED_SONAME_RELEASE "libwebp.so.7"
  )

list(APPEND _cmake_import_check_targets SDL3_image::external_libwebp )
list(APPEND _cmake_import_check_files_for_SDL3_image::external_libwebp "${_IMPORT_PREFIX}/lib/libwebp.so.7.1.8" )

# Import target "SDL3_image::external_webpdemux" for configuration "Release"
set_property(TARGET SDL3_image::external_webpdemux APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(SDL3_image::external_webpdemux PROPERTIES
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libwebpdemux.so.2.0.14"
  IMPORTED_SONAME_RELEASE "libwebpdemux.so.2"
  )

list(APPEND _cmake_import_check_targets SDL3_image::external_webpdemux )
list(APPEND _cmake_import_check_files_for_SDL3_image::external_webpdemux "${_IMPORT_PREFIX}/lib/libwebpdemux.so.2.0.14" )

# Import target "SDL3_image::external_libwebpmux" for configuration "Release"
set_property(TARGET SDL3_image::external_libwebpmux APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(SDL3_image::external_libwebpmux PROPERTIES
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libwebpmux.so.3.0.13"
  IMPORTED_SONAME_RELEASE "libwebpmux.so.3"
  )

list(APPEND _cmake_import_check_targets SDL3_image::external_libwebpmux )
list(APPEND _cmake_import_check_files_for_SDL3_image::external_libwebpmux "${_IMPORT_PREFIX}/lib/libwebpmux.so.3.0.13" )

# Commands beyond this point should not need to know the version.
set(CMAKE_IMPORT_FILE_VERSION)
