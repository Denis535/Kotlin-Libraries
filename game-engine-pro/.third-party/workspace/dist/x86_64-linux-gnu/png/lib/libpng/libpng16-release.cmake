#----------------------------------------------------------------
# Generated CMake target import file for configuration "Release".
#----------------------------------------------------------------

# Commands may need to know the format version.
set(CMAKE_IMPORT_FILE_VERSION 1)

# Import target "png16" for configuration "Release"
set_property(TARGET png16 APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(png16 PROPERTIES
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "/workspace/dist/x86_64-linux-gnu/zlib/lib/libz.so;/usr/lib/x86_64-linux-gnu/libm.so"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libpng16.so.16.4.0"
  IMPORTED_SONAME_RELEASE "libpng16.so.16"
  )

list(APPEND _cmake_import_check_targets png16 )
list(APPEND _cmake_import_check_files_for_png16 "${_IMPORT_PREFIX}/lib/libpng16.so.16.4.0" )

# Import target "png16_static" for configuration "Release"
set_property(TARGET png16_static APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(png16_static PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "/workspace/dist/x86_64-linux-gnu/zlib/lib/libz.so;/usr/lib/x86_64-linux-gnu/libm.so"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/lib/libpng16.a"
  )

list(APPEND _cmake_import_check_targets png16_static )
list(APPEND _cmake_import_check_files_for_png16_static "${_IMPORT_PREFIX}/lib/libpng16.a" )

# Commands beyond this point should not need to know the version.
set(CMAKE_IMPORT_FILE_VERSION)
